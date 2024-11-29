const express = require('express');
const router = express.Router();
const Comment = require('../models/comment');

//store the comment information (where user can post their comment)
router.post('/', async (req, res) => {
    const { owner, guest, comment } = req.body;

    try {
        const newComment = new Comment({
            owner,
            guest,
            comment,
        });

        newComment.save(function(err,result){
            if (err){
                console.log(err);
            }
            else{
                console.log(result)
            }
        });

        res.status(201).json({ message: 'Comment saved successfully', comment: newComment });
    }catch (error) {
        console.error('Error fetching user details:', error);
        res.status(500).json({ error: 'Failed to retrieve comment information' });
    }
})

module.exports = router;