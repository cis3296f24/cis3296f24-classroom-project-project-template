import React from 'react';
import { Link, Route, Routes } from 'react-router-dom';
import Home from './components/Home';
import Task from './components/Task';

function App() {
  return (
    <div className="App">
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
