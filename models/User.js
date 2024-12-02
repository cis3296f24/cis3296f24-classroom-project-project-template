// User model

const mongoose = require('mongoose');

const UserSchema = new mongoose.Schema({
    username: { type: String, required: true, unique: true },
    password: { type: String, required: true },
    friends: { type: [String], default: [] }, // List of friends
    screenshot: { type: String, default: null }, // Path to uploaded screenshot
    uploadDate: { type: Date, default: null }   // Date of last screenshot upload
  });
  
  module.exports = mongoose.model("User", UserSchema);