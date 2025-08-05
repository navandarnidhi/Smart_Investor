import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { getToken } from '../utils/auth';
import { Link } from 'react-router-dom';

function Dashboard() {
  const [portfolio, setPortfolio] = useState(null);
  const [balance, setBalance] = useState(0);
  const [orders, setOrders] = useState([]);
  const [kycStatus, setKycStatus] = useState('');
  const [indices, setIndices] = useState({ nifty: null, sensex: null });

  useEffect(() => {
    const token = getToken();
    const email = JSON.parse(atob(token.split('.')[1])).sub;

    axios.get(`http://localhost:8080/api/users/email/${email}`).then(res => {
      const user = res.data;
      fetchPortfolio(user.id);
      fetchBalance(user.id);
      fetchOrders(user.id);
      setKycStatus(user.kycCompleted ? '✅ Completed' : '❌ Pending');
    });

    axios.get('http://localhost:8080/api/marketdata/index').then(res => {
      setIndices({ nifty: res.data.nifty, sensex: res.data.sensex });
    });
  }, []);

  const fetchPortfolio = async (uid) => {
    const res = await axios.get(`http://localhost:8080/api/portfolio/${uid}`);
    setPortfolio(res.data);
  };

  const fetchBalance = async (uid) => {
    const res = await axios.get(`http://localhost:8080/api/funds/balance/${uid}`);
    setBalance(res.data);
  };

  const fetchOrders = async (uid) => {
    const res = await axios.get(`http://localhost:8080/api/orders/${uid}?limit=5`);
    setOrders(res.data);
  };

  return (
    <>
      <nav className="navbar navbar-expand-lg navbar-dark bg-dark">
        <div className="container-fluid">
          <Link className="navbar-brand" to="/dashboard">Smart Investor</Link>
          <div className="collapse navbar-collapse">
            <ul className="navbar-nav ms-auto mb-2 mb-lg-0">
              <li className="nav-item"><Link className="nav-link" to="/funds">Funds</Link></li>
              <li className="nav-item"><Link className="nav-link" to="/portfolio">Portfolio</Link></li>
              <li className="nav-item"><Link className="nav-link" to="/orders">Orders</Link></li>
              <li className="nav-item"><Link className="nav-link" to="/reports">Reports</Link></li>
              <li className="nav-item"><Link className="nav-link" to="/kyc">KYC</Link></li>
            </ul>
          </div>
        </div>
      </nav>

      <div className="container mt-4 text-center">
        <h3>Welcome to your Dashboard</h3>
        <div className="row mt-4 justify-content-center">
          <div className="col-md-3">
            <div className="card p-3 mb-3">
              <h5>Portfolio Summary</h5>
              {portfolio ? (
                <>
                  <p>Total Invested: ₹{portfolio.totalInvested}</p>
                  <p>Current Value: ₹{portfolio.currentValue}</p>
                  <p>P&L: ₹{portfolio.totalPnL}</p>
                </>
              ) : <p>Loading...</p>}
            </div>
          </div>
          <div className="col-md-3">
            <div className="card p-3 mb-3">
              <h5>Funds</h5>
              <p>Current Balance: ₹{balance}</p>
            </div>
          </div>
          <div className="col-md-3">
            <div className="card p-3 mb-3">
              <h5>KYC</h5>
              <p>Status: {kycStatus}</p>
            </div>
          </div>
        </div>

        <div className="row justify-content-center">
          <div className="col-md-5">
            <div className="card p-3 mb-3">
              <h5>Recent Orders</h5>
              <ul className="list-group">
                {orders.map((o, i) => (
                  <li key={i} className="list-group-item d-flex justify-content-between">
                    <span>{o.symbol} ({o.type})</span>
                    <span>₹{o.price} × {o.quantity}</span>
                  </li>
                ))}
              </ul>
            </div>
          </div>

          <div className="col-md-5">
            <div className="card p-3 mb-3">
              <h5>Live Index</h5>
              <p>NIFTY: {indices.nifty ?? '...loading'}</p>
              <p>SENSEX: {indices.sensex ?? '...loading'}</p>
            </div>
          </div>
        </div>
      </div>
    </>
  );
}

export default Dashboard;
