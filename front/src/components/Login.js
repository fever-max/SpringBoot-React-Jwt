import React, { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { GoogleOAuthProvider, GoogleLogin } from '@react-oauth/google';
import { jwtDecode } from 'jwt-decode';
import axios from 'axios';

function Login() {
  const googleOauthClientId = process.env.REACT_APP_GOOGLE_OAUTH_CLIENT_ID;

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
      const response = await axios.post('http://localhost:8080/login', user, {
        withCredentials: true,
      });

      if (response) {
        alert('로그인 성공! ');
        const jwtToken = response.headers.authorization; // "Authorization" 헤더에서 JWT 토큰 추출
        console.log(response.headers);
        console.log('전달받은 토큰: ' + jwtToken);

        navigate('/home', { state: { jwt: jwtToken } });
      }
    } catch (error) {
      console.log('로그인 에러: ', error);
    }
  };

  const config = {
    headers: {
      'Content-Type': 'application/json; charset=utf-8',
    },
  };

  const responseGoogle = async (response) => {
    console.log(response.credential);
    const decodedToken = jwtDecode(response.credential);
    const data = JSON.stringify(decodedToken);
    console.log('data' + data);
    let jwtToken = await axios.post('http://localhost:8080/oauth/jwt/google', data, config);
    if (jwtToken.status === 200) {
      console.log('소셜 로그인 완료');
      console.log('jwtToken: ' + jwtToken.data);
      navigate('/home', { state: { jwt: jwtToken.data } });
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
      <GoogleOAuthProvider clientId={googleOauthClientId}>
        <GoogleLogin onSuccess={responseGoogle} onFailure={responseGoogle} buttonText="Login" cookiePolicy={'single_host_origin'} />
      </GoogleOAuthProvider>

      <Link to="/join">
        <button>회원가입</button>
      </Link>
    </div>
  );
}

export default Login;
