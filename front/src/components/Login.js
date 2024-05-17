import React, { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import axios from 'axios';

function Login() {
  const navigate = useNavigate();

  const [user, setUser] = useState({
    userEmail: '',
    password: '',
  });

  const handleChange = (e) => {
    const { name, value } = e.target;
    setUser({ ...user, [name]: value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const response = await axios.post(
        'http://localhost:8080/login',
        {
          userEmail: user.userEmail,
          password: user.password,
        },
        {
          withCredentials: true,
        }
      );

      if (response) {
        alert('로그인 성공! ');
        const jwtToken = response.headers.authorization; // "Authorization" 헤더에서 JWT 토큰 추출
        console.log(response.headers);
        console.log('전달받은 토큰: ' + jwtToken);

        navigate('/userInfo', { state: { jwt: jwtToken } });
      }
    } catch (error) {
      console.log('로그인 에러: ', error);
    }
  };

  return (
    <div>
      <h3>로그인</h3>
      <form onSubmit={handleSubmit}>
        <input type="text" name="userEmail" placeholder="이메일" value={user.userEmail} onChange={handleChange} />
        <input type="password" name="password" placeholder="비밀번호" value={user.password} onChange={handleChange} />
        <button type="submit">로그인</button>
      </form>
      <Link to="/join">
        <button>회원가입</button>
      </Link>
    </div>
  );
}

export default Login;
