import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { getToken } from '../utils/auth';

function AdminPanel() {
  const [users, setUsers] = useState([]);
  const [funds, setFunds] = useState([]);
  const [orders, setOrders] = useState([]);

  const fetchAllData = async () => {
    const token = getToken();
    const isAdmin = JSON.parse(atob(token.split('.')[1])).roles?.includes("ROLE_ADMIN") || true;
    if (!isAdmin) return;

    try {
      const [userRes, fundRes, orderRes] = await Promise.all([
        axios.get(`http://localhost:8080/api/admin/users`),
        axios.get(`http://localhost:8080/api/admin/funds`),
        axios.get(`http://localhost:8080/api/admin/orders`)
      ]);
      setUsers(userRes.data);
      setFunds(fundRes.data);
      setOrders(orderRes.data);
    } catch (err) {
      console.error("Admin data fetch failed", err);
    }
  };

  useEffect(() => {
    fetchAllData();
  }, []);

  const handleKyc = async (userId, action) => {
    try {
      await axios.post(`http://localhost:8080/api/admin/kyc/${action}/${userId}`);
      alert(`KYC ${action}ed`);
      fetchAllData();
    } catch (err) {
      alert('Failed to update KYC');
    }
  };

  return (
    <div className="container mt-4">
      <h3>Admin Panel</h3>

      <h5 className="mt-4">Pending KYC Approvals</h5>
      <table className="table table-sm table-bordered">
        <thead>
          <tr>
            <th>Name</th>
            <th>Email</th>
            <th>KYC</th>
            <th>Action</th>
          </tr>
        </thead>
        <tbody>
          {users.filter(u => !u.kycCompleted).map((user) => (
            <tr key={user.id}>
              <td>{user.fullName}</td>
              <td>{user.email}</td>
              <td>{user.kycCompleted ? '✅' : '❌'}</td>
              <td>
                <button className="btn btn-success btn-sm me-2" onClick={() => handleKyc(user.id, 'approve')}>Approve</button>
                <button className="btn btn-danger btn-sm" onClick={() => handleKyc(user.id, 'reject')}>Reject</button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>

      <h5 className="mt-4">All Users</h5>
      <table className="table table-bordered">
        <thead>
          <tr>
            <th>Name</th>
            <th>Email</th>
            <th>Verified</th>
            <th>KYC</th>
          </tr>
        </thead>
        <tbody>
          {users.map((user) => (
            <tr key={user.id}>
              <td>{user.fullName}</td>
              <td>{user.email}</td>
              <td>{user.verified ? '✅' : '❌'}</td>
              <td>{user.kycCompleted ? '✅' : '❌'}</td>
            </tr>
          ))}
        </tbody>
      </table>

      <h5 className="mt-4">Recent Fund Transactions</h5>
      <table className="table table-bordered">
        <thead>
          <tr>
            <th>User ID</th>
            <th>Type</th>
            <th>Amount</th>
            <th>Time</th>
          </tr>
        </thead>
        <tbody>
          {funds.map((f, i) => (
            <tr key={i}>
              <td>{f.userId}</td>
              <td>{f.type}</td>
              <td>{f.amount}</td>
              <td>{new Date(f.timestamp).toLocaleString()}</td>
            </tr>
          ))}
        </tbody>
      </table>

      <h5 className="mt-4">Recent Orders</h5>
      <table className="table table-bordered">
        <thead>
          <tr>
            <th>User ID</th>
            <th>Symbol</th>
            <th>Type</th>
            <th>Qty</th>
            <th>Status</th>
          </tr>
        </thead>
        <tbody>
          {orders.map((o, i) => (
            <tr key={i}>
              <td>{o.userId}</td>
              <td>{o.symbol}</td>
              <td>{o.type}</td>
              <td>{o.quantity}</td>
              <td>{o.status}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}

export default AdminPanel;
