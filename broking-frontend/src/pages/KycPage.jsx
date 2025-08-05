// src/pages/KycPage.jsx
import React, { useEffect, useState } from 'react';
import { submitKyc, getKycStatus } from '../api/kyc';
import { getToken } from '../utils/auth';
import axios from 'axios';

function KycPage() {
  const [kycData, setKycData] = useState({ userId: '', pan: '', aadhaar: '', status: '' });
  const [submitted, setSubmitted] = useState(false);

  const handleChange = (e) => {
    setKycData({ ...kycData, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const token = getToken();
      const email = JSON.parse(atob(token.split('.')[1])).sub;
      const userRes = await axios.get('http://localhost:8080/api/admin/users', {
        headers: { Authorization: `Bearer ${token}` },
      });
      const currentUser = userRes.data.find((u) => u.email === email);

      const payload = {
        userId: currentUser.id,
        pan: kycData.pan,
        aadhaar: kycData.aadhaar,
      };

      await submitKyc(payload);
      setSubmitted(true);
    } catch (err) {
      console.error(err);
      alert('Error submitting KYC');
    }
  };

  useEffect(() => {
    const fetchStatus = async () => {
      try {
        const token = getToken();
        const email = JSON.parse(atob(token.split('.')[1])).sub;
        const userRes = await axios.get('http://localhost:8080/api/admin/users', {
          headers: { Authorization: `Bearer ${token}` },
        });
        const currentUser = userRes.data.find((u) => u.email === email);
        if (currentUser) {
          const kycRes = await getKycStatus(currentUser.id);
          setKycData((prev) => ({ ...prev, userId: currentUser.id, status: kycRes.data.status }));
        }
      } catch (err) {
        console.error('KYC status fetch failed');
      }
    };
    fetchStatus();
  }, []);

  return (
    <div className="container mt-4" style={{ maxWidth: '600px' }}>
      <h3 className="mb-3">KYC Submission</h3>
      {submitted && <div className="alert alert-success">KYC submitted successfully!</div>}
      {kycData.status && <div className="alert alert-info">Current KYC Status: {kycData.status}</div>}
      <form onSubmit={handleSubmit}>
        <div className="mb-3">
          <label>PAN</label>
          <input className="form-control" name="pan" value={kycData.pan} onChange={handleChange} required />
        </div>
        <div className="mb-3">
          <label>Aadhaar</label>
          <input className="form-control" name="aadhaar" value={kycData.aadhaar} onChange={handleChange} required />
        </div>
        <button className="btn btn-primary w-100">Submit KYC</button>
      </form>
    </div>
  );
}

export default KycPage;
