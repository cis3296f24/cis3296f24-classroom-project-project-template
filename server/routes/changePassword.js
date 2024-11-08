const express = require('express');
const bcrypt = require('bcrypt');
const jwt = require('jsonwebtoken');
const User = require('../models/user');
const router = express.Router();

//Still Under Construction

router.patch('/', async (req, res) => {
    const { userId, currentPassword, newPassword } = req.body;

    try {
        //check if user exists
        const user = await User.findById(userId);
        if (!user) {
            return res.status(404).json({ error: 'User not found' });
        }

        const isPasswordValid = await bcrypt.compare(currentPassword, user.password);
        

        //encrypt password
        const hashedPassword = await bcrypt.hash(password, 10);




    }catch {

    }
})