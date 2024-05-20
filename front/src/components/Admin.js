import React, { useEffect } from 'react';
import axios from 'axios';
import { useLocation } from 'react-router-dom';

function Admin() {
  const location = useLocation();
  const jwtToken = location.state?.jwt;

  useEffect(() => {
    const fetchAdminData = async () => {
      try {
        const response = await axios.get('http://localhost:8080/admin', {
          headers: {
            Authorization: jwtToken,
          },
        });
        alert(response.data);
      } catch (error) {
        console.error('관리자 페이지 요청 에러:', error);
        alert('관리자 권한이 아닙니다.');
        window.location.href = '/';
      }
    };
    fetchAdminData();
  }, []);

  return <div>관리자 페이지입니다.</div>;
}

export default Admin;
