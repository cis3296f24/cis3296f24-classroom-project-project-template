import React, { useState } from 'react';
import { ChevronLeft, ChevronRight, Plus, X, CalendarDays } from 'lucide-react';

const Task = () => {
  const [currentDate, setCurrentDate] = useState(new Date());
  const [tasks, setTasks] = useState({});
  const [selectedDate, setSelectedDate] = useState(null);
  const [showTaskInput, setShowTaskInput] = useState(false);
  const [newTask, setNewTask] = useState('');
  
  const daysInMonth = new Date(
    currentDate.getFullYear(),
    currentDate.getMonth() + 1,
    0
  ).getDate();
  
  const firstDayOfMonth = new Date(
    currentDate.getFullYear(),
    currentDate.getMonth(),
    1
  ).getDay();
  
  const monthNames = [
    "January", "February", "March", "April", "May", "June",
    "July", "August", "September", "October", "November", "December"
  ];
  
  const days = ["Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"];
  
  const prevMonth = () => {
    setCurrentDate(new Date(currentDate.getFullYear(), currentDate.getMonth() - 1));
  };
  
  const nextMonth = () => {
    setCurrentDate(new Date(currentDate.getFullYear(), currentDate.getMonth() + 1));
  };

  const isToday = (day) => {
    const today = new Date();
    return day === today.getDate() &&
      currentDate.getMonth() === today.getMonth() &&
      currentDate.getFullYear() === today.getFullYear();
  };

  const getDateKey = (day) => {
    return `${currentDate.getFullYear()}-${currentDate.getMonth() + 1}-${day}`;
  };

  const handleDateClick = (day) => {
    const dateKey = getDateKey(day);
    setSelectedDate(dateKey);
    setShowTaskInput(true);
  };

  const addTask = (e) => {
    e.preventDefault();
    if (newTask.trim() && selectedDate) {
      setTasks(prev => ({
        ...prev,
        [selectedDate]: [...(prev[selectedDate] || []), newTask.trim()]
      }));
      setNewTask('');
      setShowTaskInput(false);
    }
  };

  const removeTask = (dateKey, taskIndex) => {
    setTasks(prev => ({
      ...prev,
      [dateKey]: prev[dateKey].filter((_, index) => index !== taskIndex)
    }));
  };

  const getTaskCount = (day) => {
    const dateKey = getDateKey(day);
    return tasks[dateKey]?.length || 0;
  };

  return (
    <div className="w-full max-w-lg mx-auto bg-white rounded-lg shadow-lg p-4">
      <div className="flex items-center justify-between mb-4">
        <button 
          onClick={prevMonth}
          className="p-2 hover:bg-gray-100 rounded-full"
        >
          <ChevronLeft className="w-5 h-5" />
        </button>
        
        <h2 className="text-lg font-semibold">
          {monthNames[currentDate.getMonth()]} {currentDate.getFullYear()}
        </h2>
        
        <button 
          onClick={nextMonth}
          className="p-2 hover:bg-gray-100 rounded-full"
        >
          <ChevronRight className="w-5 h-5" />
        </button>
      </div>
      
      <div className="grid grid-cols-7 gap-1 mb-2">
        {days.map(day => (
          <div key={day} className="text-center text-sm font-medium text-gray-600 p-2">
            {day}
          </div>
        ))}
      </div>
      
      <div className="grid grid-cols-7 gap-1 mb-4">
        {Array.from({ length: firstDayOfMonth }).map((_, index) => (
          <div key={`empty-${index}`} className="p-2"></div>
        ))}
        
        {Array.from({ length: daysInMonth }).map((_, index) => {
          const day = index + 1;
          const taskCount = getTaskCount(day);
          return (
            <div
              key={day}
              onClick={() => handleDateClick(day)}
              className={`p-2 text-center rounded hover:bg-gray-100 cursor-pointer relative
                ${isToday(day) ? 'bg-blue-100 text-blue-600 font-semibold' : ''}`}
            >
              {day}
              {taskCount > 0 && (
                <span className="absolute top-0 right-0 bg-blue-500 text-white rounded-full w-4 h-4 text-xs flex items-center justify-center">
                  {taskCount}
                </span>
              )}
            </div>
          );
        })}
      </div>

      {selectedDate && (
        <div className="border-t pt-4">
          <h3 className="font-semibold mb-2 flex items-center">
            <CalendarDays className="w-4 h-4 mr-2" />
            Tasks for {selectedDate}
          </h3>
          <div className="space-y-2">
            {tasks[selectedDate]?.map((task, index) => (
              <div key={index} className="flex items-center justify-between bg-gray-50 p-2 rounded">
                <span>{task}</span>
                <button
                  onClick={() => removeTask(selectedDate, index)}
                  className="text-red-500 hover:text-red-700"
                >
                  <X className="w-4 h-4" />
                </button>
              </div>
            ))}
          </div>
        </div>
      )}

      {showTaskInput && (
        <form onSubmit={addTask} className="mt-4 flex gap-2">
          <input
            type="text"
            value={newTask}
            onChange={(e) => setNewTask(e.target.value)}
            placeholder="Enter new task..."
            className="flex-1 p-2 border rounded"
          />
          <button
            type="submit"
            className="bg-blue-500 text-white px-4 py-2 rounded hover:bg-blue-600 flex items-center"
          >
            <Plus className="w-4 h-4 mr-1" /> Add
          </button>
        </form>
      )}
    </div>
  );
};

export default Task;