import React from 'react';
import { Link, Route, Routes } from 'react-router-dom';
import Home from './components/Home';
import Task from './components/Task';
import Header from './Header';
import './styles/index.css'; 

function App() {
  return (
    <div className="App">
      <Header />
      <nav>
        <Link to="/">Home</Link>
        <Link to="/task">Task</Link>
      </nav>

      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/task" element={<Task />}>
        </Route>
      </Routes>
    </div>
  );
}

export default App;
