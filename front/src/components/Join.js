import React, { useEffect, useState } from 'react';
import axios from 'axios';

function Join() {
  const [user, setUser] = useState({
    username: '',
    userEmail: '',
    password: '',
  });

  const handleChange = (e) => {
    const { id, value } = e.target;
    setUser({ ...user, [id]: value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      await axios.post('http://localhost:8080/join', user);
      alert('회원가입 완료');
      window.location.href = '/';
    } catch (error) {
      console.log('회원가입 에러: ' + error);
    }
  };

  return (
    <div>
      <h3>회원가입</h3>
      <form onSubmit={handleSubmit}>
        <input type="text" id="username" value={user.username} placeholder="이름" onChange={handleChange} />
        <input type="text" id="password" value={user.password} placeholder="비밀번호" onChange={handleChange} />
        <input type="text" id="userEmail" value={user.userEmail} placeholder="이메일" onChange={handleChange} />
        <button type="submit">회원가입</button>
      </form>
    </div>
  );
}

export default Join;
