import React, { useState } from 'react';
import { register, login, verifyOtp } from '../api/auth';
import { saveToken } from '../utils/auth';
import { useNavigate } from 'react-router-dom';

function AuthPage() {
  const [isRegister, setIsRegister] = useState(false);
  const [isOtpPhase, setIsOtpPhase] = useState(false);
  const [formData, setFormData] = useState({ fullName: '', email: '', password: '', phone: '', otp: '' });
  const navigate = useNavigate();

  const handleChange = (e) => {
    setFormData((prev) => ({ ...prev, [e.target.name]: e.target.value }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      if (isOtpPhase) {
        await verifyOtp(formData.email, formData.otp);
        alert('OTP Verified. You can now log in.');
        setIsOtpPhase(false);
        setIsRegister(false);
      } else if (isRegister) {
        await register(formData);
        alert('Registered successfully. Check your email for OTP.');
        setIsOtpPhase(true);
      } else {
        const res = await login({ email: formData.email, password: formData.password });
        saveToken(res.data.replace('Bearer ', ''));
        navigate('/dashboard');
      }
    } catch (err) {
      alert(err.response?.data || 'Error occurred');
    }
  };

  return (
    <div className="container mt-5" style={{ maxWidth: '500px' }}>
      <h3 className="text-center">{isOtpPhase ? 'Verify OTP' : isRegister ? 'Register' : 'Login'}</h3>
      <form onSubmit={handleSubmit}>
        {isRegister && !isOtpPhase && (
          <>
            <div className="mb-3">
              <label className="form-label">Full Name</label>
              <input className="form-control" name="fullName" onChange={handleChange} required />
            </div>
            <div className="mb-3">
              <label className="form-label">Phone</label>
              <input className="form-control" name="phone" onChange={handleChange} required />
            </div>
          </>
        )}
        <div className="mb-3">
          <label className="form-label">Email</label>
          <input type="email" className="form-control" name="email" onChange={handleChange} required />
        </div>
        {!isOtpPhase && (
          <div className="mb-3">
            <label className="form-label">Password</label>
            <input type="password" className="form-control" name="password" onChange={handleChange} required />
          </div>
        )}
        {isOtpPhase && (
          <div className="mb-3">
            <label className="form-label">OTP</label>
            <input className="form-control" name="otp" onChange={handleChange} required />
          </div>
        )}
        <button className="btn btn-primary w-100" type="submit">
          {isOtpPhase ? 'Verify OTP' : isRegister ? 'Register' : 'Login'}
        </button>
      </form>
      {!isOtpPhase && (
        <div className="text-center mt-3">
          <button className="btn btn-link" onClick={() => {
            setIsRegister(!isRegister);
            setFormData({});
          }}>
            {isRegister ? 'Already have an account? Login' : 'New user? Register'}
          </button>
        </div>
      )}
    </div>
  );
}

export default AuthPage;
