import React from 'react';
import { Route, Routes } from 'react-router-dom';
import Home from './components/Home';
import Task from './components/Task';
import Header from './Header';
import './styles/index.css';

function App() {
  return (
    <div className="App">
      <Header />
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/task" element={<Task />}>
        </Route>
      </Routes>
    </div>
  );
}

export default App;
