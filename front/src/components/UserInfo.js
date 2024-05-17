import React from 'react';
import { useLocation } from 'react-router-dom';
import axios from 'axios';

function UserInfo() {
  const location = useLocation();
  const jwtToken = location.state.jwt;

  const handleLogout = async () => {
    try {
      const response = await axios.get('http://localhost:8080/logout', { withCredentials: true });
      alert(response.data);
      window.location.href = '/';
    } catch (error) {
      console.error('로그아웃 에러:', error);
    }
  };

  return (
    <div>
      <h1>사용자 정보</h1>
      <p>jwtToken: {jwtToken}</p>
      <button onClick={handleLogout}>로그아웃</button>
    </div>
  );
}

export default UserInfo;
