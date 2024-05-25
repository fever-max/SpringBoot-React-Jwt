import React, { useEffect, useState } from 'react';
import { useLocation } from 'react-router-dom';
import axios from 'axios';

function UserInfo() {
  const location = useLocation();
  const jwtToken = location.state.jwt;
  const [user, setUser] = useState({
    username: '',
    userEmail: '',
    roles: '',
  });

  useEffect(() => {
    const fetchUserData = async () => {
      try {
        const response = await axios.get('http://localhost:8080/user', {
          headers: {
            Authorization: jwtToken,
          },
        });
        alert('인증된 유저');
        setUser(response.data);
      } catch (error) {
        console.error('마이페이지 요청 에러:', error);
        alert('인증된 권한이 아닙니다.');
        window.location.href = '/';
      }
    };
    fetchUserData();
  }, []);

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
      <h1>사용자 세부정보</h1>
      <p>이름: {user.username}</p>
      <p>이메일: {user.userEmail}</p>
      <p>권한: {user.roles}</p>
      <button onClick={handleLogout}>로그아웃</button>
    </div>
  );
}

export default UserInfo;
