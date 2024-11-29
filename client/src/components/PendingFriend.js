import React, { useContext, useEffect, useState } from "react";
import { AuthContext } from "./helper/auth";
import { acceptFriendRequest, declineFriendRequest, getPendingFriendRequest } from "./helper/FriendAPI";

function PendingFriend() {

    const { isLoggedIn, setIsLoggedIn } = useContext(AuthContext);
    const [pendingRequests, setPendingRequests] = useState([]);
    const [error, setError] = useState("");
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        const fetchPendingRequests = async () => {
            try {
                const userId = localStorage.getItem("userId");
                console.log(userId);
                if (!userId) {
                    setError("User not logged in");
                    return;
                }

                const data = await getPendingFriendRequest(userId);
                if(!data.error){
                    setPendingRequests(data.pending || []);
                    console.log(pendingRequests);
                }
                else {
                    setError(data.error);
                    return;
                }
            } catch (err) {
                setError(err.message || "Failed to fetch pending requests");
            } finally {
                setLoading(false);
            }
        };

        if (isLoggedIn) {
            fetchPendingRequests();
        } else {
            setError("User not logged in");
            setLoading(false);
        }
    }, []);

    const handleAccept = async (friendId) => {
        try {
            const userId = localStorage.getItem("userId");
            await acceptFriendRequest(userId, friendId);
            setPendingRequests((prev) => prev.filter((req) => req.id !== friendId));
        } catch (err) {
            console.error("Failed to accept friend request:", err);
        }
    }

    const handleDecline = async (friendId) => {
        try {
            const userId = localStorage.getItem("userId");
            await declineFriendRequest(userId, friendId);
            setPendingRequests((prev) => prev.filter((req) => req.id !== friendId));
        } catch (err) {
            console.error("Failed to decline friend request:", err);
        }
    }

    return (
        <div className="min-h-screen p-4">
            <h2 className="text-2xl font-bold mb-4">Pending Friend Requests</h2>
            {pendingRequests.length === 0 ? (
                <p>No pending friend requests</p>
            ) : (
                <div className="flex flex-col gap-4">
                    {pendingRequests.map((request) => (
                        <div
                            key={request.id}
                            className="flex items-center justify-between p-4 bg-gray-800 rounded-lg shadow-md"
                        >
                            <div className="flex items-center gap-4">
                                <img
                                    src={request.avatarUrl || "/images/default-avatar.png"}
                                    alt="Avatar"
                                    className="w-12 h-12 rounded-full"
                                />
                                <div>
                                    <p className="text-lg font-medium">{request.username}</p>
                                    <p className="text-sm text-gray-400">{request.email}</p>
                                </div>
                            </div>
                            <div className="flex gap-2">
                                <button
                                    onClick={() => handleAccept(request.id)}
                                    className="px-4 py-2 bg-green-500 text-white rounded-lg hover:bg-green-400 transition"
                                >
                                    Accept
                                </button>
                                <button
                                    onClick={() => handleDecline(request.id)}
                                    className="px-4 py-2 bg-red-500 text-white rounded-lg hover:bg-red-400 transition"
                                >
                                    Decline
                                </button>
                            </div>
                        </div>
                    ))}
                </div>
            )}
        </div>
    )
}

export default PendingFriend;