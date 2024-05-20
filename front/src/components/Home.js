import React from 'react';
import { useNavigate, useLocation } from 'react-router-dom';
import axios from 'axios';

function Home() {
  const location = useLocation();
  const jwtToken = location.state.jwt;

  const navigate = useNavigate();

  const handleLogout = async () => {
    try {
      const response = await axios.get('http://localhost:8080/logout', { withCredentials: true });
      alert(response.data);
      window.location.href = '/';
    } catch (error) {
      console.error('로그아웃 에러:', error);
    }
  };

  const handleAdminClick = () => {
    navigate('/admin', { state: { jwt: jwtToken } });
  };

  const handleUser = () => {
    navigate('/userInfo', { state: { jwt: jwtToken } });
  };

  return (
    <div>
      <h1>사용자 정보</h1>
      <p>jwtToken: {jwtToken}</p>
      <button onClick={handleLogout}>로그아웃</button>
      <button onClick={handleUser}>마이 페이지</button>
      <button onClick={handleAdminClick}>관리자 페이지</button>
    </div>
  );
}

export default Home;
