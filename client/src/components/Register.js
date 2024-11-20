import { useState } from 'react';
import { NavLink, useNavigate } from 'react-router-dom';
import EarthBg from '../assets/earth.png';


function Register() {
    const [username, setUserName] = useState('');
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [confirmPassword, setConfirmPassword] = useState('');
    const [message, setMessage] = useState('');

    const navigate = useNavigate();

    const handlePassword = () => {
        if (password !== confirmPassword) {
            setMessage('Password not match');
            return false;
        }
        else {
            return true;
        }
    }

    const handleSubmit = async(e) => {
        e.preventDefault();
        if(!handlePassword()) {
            return;
        }

        fetch('http://localhost:9000/api/register', {
            method: 'POST',
            headers: {
                'Content-type': 'application/json; charset=UTF-8',
            },
            body: JSON.stringify({username, email, password}),
        })
        .then((response) => response.json())
        .then((data) => {
            if(!data.error) {
                console.log('Login Successful:', data);
                setMessage('Login successful!');

                localStorage.setItem('token', data.token);
                localStorage.setItem('userId', data.userId);
                localStorage.setItem('username', data.userName);
                localStorage.setItem('email', data.userEmail);

                navigate('/');
                window.location.reload();
            }
            else {
                console.log('Login failed:', data.error);
                setMessage(data.error);
            }
        })
        .catch((err) => {
            console.log(err.message);
            setMessage('An error occurred. Please try again.');
        });
    }

    return (
        <div className="flex flex-col min-h-screen lg:flex-row">
            <div className="left relative flex flex-col items-center justify-center w-full min-h-screen bg-neutral-900 p-8 lg:h-full lg:w-1/2">
                <div className="absolute top-4 left-4">
                    <NavLink to="/" className="text-sm font-semibold text-normal hover:text-white transition">
                        <span aria-hidden="true">&#128072;</span> Back
                    </NavLink>
                </div>
    
                <form
                    onSubmit={handleSubmit}
                    className="mt-8 w-11/12 max-w-md space-y-4 bg-neutral-800 p-6 rounded-lg shadow-lg"
                >
                    <h2 className="text-2xl font-bold text-white text-center mb-5">Register</h2>

                    <div>
                        <label htmlFor="username" className="block text-gray-200 font-semibold mb-1">User Name</label>
                        <input
                            type="text"
                            id="username"
                            name="username"
                            placeholder="Please enter a user name here..."
                            onChange={(e) => setUserName(e.target.value)}
                            required
                            className="w-full px-4 py-2 bg-neutral-700 text-gray-100 rounded-md focus:outline-none focus:ring-2 focus:ring-violet-400"
                        />
                    </div>
    
                    <div>
                        <label htmlFor="email" className="block text-gray-200 font-semibold mb-1">Email</label>
                        <input
                            type="email"
                            id="email"
                            name="email"
                            placeholder="Please enter email here..."
                            onChange={(e) => setEmail(e.target.value)}
                            required
                            className="w-full px-4 py-2 bg-neutral-700 text-gray-100 rounded-md focus:outline-none focus:ring-2 focus:ring-violet-400"
                        />
                    </div>
    
                    <div>
                        <label htmlFor="password" className="block text-gray-200 font-semibold mb-1">Password</label>
                        <input
                            type="password"
                            id="password"
                            name="password"
                            placeholder="Please enter password here..."
                            onChange={(e) => setPassword(e.target.value)}
                            required
                            className="w-full px-4 py-2 bg-neutral-700 text-gray-100 rounded-md focus:outline-none focus:ring-2 focus:ring-violet-400"
                        />
                    </div>

                    <div>
                        <label htmlFor="confirmPassword" className="block text-gray-200 font-semibold mb-1">Confirm Password</label>
                        <input
                            type="password"
                            id="confirmPassword"
                            name="confirmPassword"
                            placeholder="Please enter password here..."
                            onChange={(e) => setConfirmPassword(e.target.value)}
                            required
                            className="w-full px-4 py-2 bg-neutral-700 text-gray-100 rounded-md focus:outline-none focus:ring-2 focus:ring-violet-400"
                        />
                    </div>
    
                    <button
                        type="submit"
                        className="w-full py-2 bg-gradient-to-r from-violet-500 to-fuchsia-500 hover:bg-gradient-to-r hover:from-violet-400 hover:to-fuchsia-400 text-white font-bold rounded-md transition-transform duration-500 transform hover:scale-105"
                    >
                        Register
                    </button>
    
                    <NavLink to="/login">
                        <button
                            type="button"
                            className="w-full py-2 mt-4 bg-gradient-to-r from-teal-500 to-blue-400 hover:bg-gradient-to-r hover:from-teal-400 hover:to-blue-400 text-white font-bold rounded-md transition-transform duration-500 transform hover:scale-105"
                        >
                            Back To Login
                        </button>
                    </NavLink>
    
                    <p className="mt-2 text-sm text-gray-300 text-center h-4">{message}</p>
                </form>
            </div>
    
            <div
                className="right w-1/2 bg-cover bg-center lg:min-h-screen lg:w-1/2"
                style={{
                    backgroundImage: `url(${EarthBg})`,
                }}
            ></div>
        </div>
    );

}

export default Register;