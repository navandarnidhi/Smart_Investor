import axios from 'axios';
import { getToken } from '../utils/auth';

// Utility to return Authorization header
const authHeader = () => ({
  headers: {
    Authorization: `Bearer ${getToken()}`
  }
});

// Submit KYC
export const submitKyc = (kycData) => {
  return axios.post(
    'http://localhost:8080/api/kyc/submit',
    kycData,
    authHeader()
  );
};

// Get KYC status by userId
export const getKycStatus = (userId) => {
  return axios.get(
    `http://localhost:8080/api/kyc/status/${userId}`,
    authHeader()
  );
};
