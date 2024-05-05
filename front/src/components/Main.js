import React from 'react';
import { Routes, Route } from 'react-router-dom';
import Login from './Login';
import Join from './Join';
import UserInfo from './UserInfo';

function Main() {
  return (
    <div>
      <Routes>
        <Route path="/" element={<Login />} />
        <Route path="/join" element={<Join />} />
        <Route path="/userInfo" element={<UserInfo />} />
      </Routes>
    </div>
  );
}

export default Main;
