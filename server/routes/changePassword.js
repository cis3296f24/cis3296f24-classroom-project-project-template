const express = require('express');
const bcrypt = require('bcrypt');
const jwt = require('jsonwebtoken');
const User = require('../models/user');
const nodemailer = require('nodemailer');
const router = express.Router();

// Temporary storage for verification codes
const verificationCodes = new Map();

router.post('/', async (req, res) => {
    const { email, currentPassword, newPassword, verificationCode } = req.body;

    try {
        // Check if user exists
        const user = await User.findOne({ email });
        if (!user) {
            return res.status(400).json({ error: "User not found" });
        }

        // Verify current password
        const isPasswordValid = await bcrypt.compare(currentPassword, user.password);
        if (!isPasswordValid) {
            return res.status(401).json({ error: "Current password is incorrect" });
        }

        // If no verification code is provided, generate and send it
        if (!verificationCode) {
            const code = Math.floor(100000 + Math.random() * 900000).toString(); // 6-digit code
            const expiresAt = Date.now() + 10 * 60 * 1000; // Expires in 10 minutes
            verificationCodes.set(email, { code, expiresAt });

            // Send email
            const transporter = nodemailer.createTransport({
                service: 'Gmail',
                auth: {
                    user: process.env.EMAIL_USER,
                    pass: process.env.EMAIL_PASS  
                }
            });

            const mailOptions = {
                from: process.env.EMAIL_USER,
                to: email,
                subject: 'Password Change Verification Code',
                text: `Your verification code is ${code}. It will expire in 10 minutes.`
            };

            await transporter.sendMail(mailOptions);
            return res.status(200).json({ message: "Verification code sent to email" });
        }

        // Validate verification code
        const storedCode = verificationCodes.get(email);
        if (!storedCode || storedCode.code !== verificationCode || storedCode.expiresAt < Date.now()) {
            return res.status(400).json({ error: "Invalid or expired verification code" });
        }

        // Verification successful, clear the code
        verificationCodes.delete(email);

        // Hash the new password
        const hashedNewPassword = await bcrypt.hash(newPassword, 10);

        // Update password in database
        user.password = hashedNewPassword;
        await user.save();

        // Respond
        res.status(200).json({ message: "Password changed successfully" });
    } catch (error) {
        console.error("Error changing password:", error);
        res.status(500).json({ error: "Password change failed" });
    }
});

module.exports = router;