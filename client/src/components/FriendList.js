import React, { useState } from 'react';
import EastIcon from '@mui/icons-material/East';
import 'animate.css';
import Bar from './helper/Bar';

function FriendsList() {
    const [friends, setFriends] = useState([]);
    const [searchTerm, setSearchTerm] = useState('');
    const [newFriendName, setNewFriendName] = useState('');

    // Add friend functionality
    const handleAddFriend = () => {
        if (newFriendName.trim()) {
            const newFriend = {
                id: friends.length + 1,
                name: newFriendName.trim()
            };
            setFriends([...friends, newFriend]);
            setNewFriendName(''); // Clear input after adding
        }
    };

    // Search functionality
    const filteredFriends = friends.filter(friend => 
        friend.name.toLowerCase().includes(searchTerm.toLowerCase())
    );

    return (
        <div className="friends-list flex flex-col justify-center items-center">
            <div className="my-20 mx-3 text-center">
                <h1 className="text-5xl sm:text-6xl md:text-7xl lg:text-8xl pixelify-sans-bold px-2 animate__animated animate__flipInX header-text-gradient">
                    FRIENDS
                </h1>
            </div>

            <Bar title="Search Friends"></Bar>

            {/* Search and Add Friend Section */}
            <div className="flex flex-col sm:flex-row justify-center items-center gap-4 w-10/12 mb-10">
                <input 
                    type="text"
                    placeholder="Search friends..."
                    value={searchTerm}
                    onChange={(e) => setSearchTerm(e.target.value)}
                    className="w-full sm:w-1/2 p-3 rounded-lg border-2 border-gray-300 text-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
                />
                <div className="flex gap-2 w-full sm:w-1/2">
                    <input 
                        type="text"
                        placeholder="Add a friend: Include first and last name"
                        value={newFriendName}
                        onChange={(e) => setNewFriendName(e.target.value)}
                        className="flex-grow p-3 rounded-lg border-2 border-gray-300 text-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
                    />
                    <button 
                        onClick={handleAddFriend}
                        className="bg-slate-300 p-3 rounded-lg hover:bg-slate-400 transition duration-300 flex items-center justify-center"
                    >
                        <EastIcon />
                    </button>
                </div>
            </div>

            {friends.length > 0 && (
                <>
                    <Bar title="Your Friends"></Bar>

                    {/* Friends List */}
                    <div className="grid grid-cols-1 gap-6 mt-12 mb-10 text-lg w-10/12 sm:grid-cols-3">
                        {filteredFriends.map((friend) => (
                            <div 
                                key={friend.id}
                                className="bg-white p-6 rounded-lg shadow-lg max-w-xs w-full hover:bg-slate-100 transition-all duration-300"
                            >
                                <h3 className='text-gray-800 text-lg font-semibold'>{friend.name}</h3>
                                <div className="flex justify-between mt-4">
                                    <button 
                                        className="bg-slate-300 px-4 py-2 rounded-lg hover:bg-slate-400 transition duration-300"
                                    >
                                        Progress
                                    </button>
                                    <button 
                                        className="bg-red-300 px-4 py-2 rounded-lg hover:bg-red-400 transition duration-300"
                                        onClick={() => setFriends(friends.filter(f => f.id !== friend.id))}
                                    >
                                        Remove
                                    </button>
                                </div>
                            </div>
                        ))}
                    </div>
                </>
            )}
        </div>
    );
}

export default FriendsList;