import 'animate.css';
import React, { useContext, useEffect, useState } from 'react';
import { NavLink, useNavigate } from 'react-router-dom';
import { AuthContext } from './helper/auth';
import Friend from './helper/Friend';
import { getFriendsList, sendFriendRequest } from './helper/FriendAPI';

function FriendList() {

    const [friends, setFriends] = useState([]);
    const [error, setError] = useState('');
    const [loading, setLoading] = useState(true);

    //implement the add friend logic
    const [friendId, setFriendId] = useState('');
    const [message, setMessage] = useState('');

    //check user logged in or not
    const { isLoggedIn, setIsLoggedIn } = useContext(AuthContext);

    const navigate = useNavigate();

    //fetch the friend list
    useEffect(() => {
        const fetchFriendData = async () => {
            if(!isLoggedIn){
                setLoading(false);
                setError("User Not Logged In");
                return;
            }

            try {
                const userId = localStorage.getItem('userId');
                const data = await getFriendsList(userId);
                setFriends(data.friends);
            } catch (err) {
                setError(err.message || 'Failed to fetch friends list');
            } finally {
                setLoading(false);
            }
        }

        fetchFriendData();
    }, []);

    //send a friend request
    const addFriend = async () => {
        if(!isLoggedIn){
            setError("User Not Logged In");
            return;
        }

        if(friendId === '' || !friendId) {
            setMessage("Please Do Not Leave The Input Box Blank :)");
            return;
        }
        try {
            const userId = localStorage.getItem('userId');
            const data = await sendFriendRequest(userId, friendId);
            if(!data.error){
                setMessage(data.message);
                setFriendId('');
            }
            else {
                setMessage(data.error);
            }
        } catch (err) {
            console.log(err);
            setMessage(err);
        }

    }

    if (loading) return (
        <div className="text-center mt-12 text-blue-500">
            <h2>Loading...</h2>
        </div>
    );

    if (error) return (
        <div className="text-center mt-12 text-red-500">
            <h2>{error}</h2>
            <NavLink to="/login" className="text-blue-500 underline">
            Go to Login
            </NavLink>
        </div>
    );

    const handleProfileClick = (userId) => {
        navigate(`/profile/${userId}`);
    }

    return (
        <div className="min-h-screen pb-2 px-8 my-10 flex justify-center text-gray-300">
            <div className="flex flex-col items-center w-full bg-neutral-800 rounded-md p-8">
                    <h1 className="text-5xl sm:text-4xl md:text-5xl lg:text-6xl pixelify-sans-bold px-2 header-text-gradient">
                            FRIENDS
                    </h1>
                    <div className="border-t-2 w-full border-gray-500"></div>
                    <div className="w-full flex justify-between items-center">
                        <NavLink to="/challenge" className="text-sm text-blue-400 font-medium hover:underline">
                            👈 Challenge
                        </NavLink>
                        <NavLink to="/pendingFriend" className="text-sm text-blue-400 font-medium hover:underline">
                            Pending List 👉
                        </NavLink>
                    </div>

                    <div className="w-full p-2 rounded-md bg-gradient-to-r from-violet-500 to-fuchsia-500 flex flex-col items-center h-11/12 my-8 animate__animated animate__tada">
                        <h2 className="text-3xl font-bold my-8"> Add A Friend </h2>
                        <div className="flex w-full justify-center gap-4 items-center">
                            <input
                                onChange={(e) => setFriendId(e.target.value)}
                                required
                                placeholder="Input user ID here..."
                                className="flex-1 p-3 bg-neutral-700 text-gray-100 rounded-md focus:outline-none focus:ring-2 focus:ring-violet-100"
                            />
                            <button
                                onClick={addFriend}
                                className="px-6 py-3 bg-gradient-to-r from-teal-500 to-blue-400 hover:bg-gradient-to-r hover:from-teal-400 hover:to-blue-400 text-white font-bold rounded-md transition-transform duration-500 transform hover:scale-105"
                            >
                                Add
                            </button>
                        </div>
                        <p className="mt-4 mb-2 text-gray-200">{message}</p>
                    </div>

                    <h1 className="text-3xl font-bold mb-4 mt-8"> Friend List </h1>
                    <div className="border-t-2 w-full border-gray-500"></div>
                    {friends.length === 0 ? (
                        <p className="mt-2 text-gray-500">No friends found</p>
                    ) : (<div className="mt-8 flex flex-col items-center gap-6 w-full">
                            {friends.map(friend => (
                                <button onClick={() => handleProfileClick(friend._id)}key={friend._id} className="bg-neutral-700 rounded-md p-4 min-w-[280px] sm:w-[420px] lg:w-10/12 shadow-lg transition-transform transform hover:scale-105">
                                    <Friend friend={friend} />
                                </button>
                            )
                            )}
                    </div>)}
                </div>
            </div>
    )
}

export default FriendList;