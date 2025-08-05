import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { getToken } from '../utils/auth';

function Portfolio() {
  const [userId, setUserId] = useState('');
  const [holdings, setHoldings] = useState([]);
  const [pnl, setPnl] = useState(0);

  useEffect(() => {
    const fetchUserId = async () => {
      const token = getToken();
      const email = JSON.parse(atob(token.split('.')[1])).sub;
      const userRes = await axios.get(`http://localhost:8080/api/admin/users`);
      const currentUser = userRes.data.find((u) => u.email === email);
      if (currentUser) {
        setUserId(currentUser.id);
        fetchHoldings(currentUser.id);
      }
    };
    fetchUserId();
  }, []);

  const fetchHoldings = async (uid) => {
    try {
      const [holdingsRes, pnlRes] = await Promise.all([
        axios.get(`http://localhost:8080/api/portfolio/holdings/${uid}`),
        axios.get(`http://localhost:8080/api/portfolio/pnl/${uid}`)
      ]);
      setHoldings(holdingsRes.data);
      setPnl(pnlRes.data);
    } catch (err) {
      console.error('Error loading portfolio', err);
    }
  };

  return (
    <div className="container mt-4">
      <h3 className="mb-3">Your Portfolio</h3>
      <h5>Total P&L: ₹{pnl}</h5>

      <table className="table table-bordered mt-3">
        <thead>
          <tr>
            <th>Symbol</th>
            <th>Quantity</th>
            <th>Average Price</th>
            <th>Current Price</th>
            <th>Unrealized P&L</th>
          </tr>
        </thead>
        <tbody>
          {holdings.map((h, index) => (
            <tr key={index}>
              <td>{h.symbol}</td>
              <td>{h.quantity}</td>
              <td>{h.avgPrice}</td>
              <td>{h.currentPrice}</td>
              <td>{h.unrealizedPnL}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}

export default Portfolio;
