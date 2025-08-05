import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { getToken } from '../utils/auth';

function Trading() {
  const [userId, setUserId] = useState('');
  const [symbol, setSymbol] = useState('');
  const [quantity, setQuantity] = useState('');
  const [orderType, setOrderType] = useState('BUY');
  const [orders, setOrders] = useState([]);

  useEffect(() => {
    const fetchUserId = async () => {
      const token = getToken();
      const email = JSON.parse(atob(token.split('.')[1])).sub;
      const userRes = await axios.get(`http://localhost:8080/api/admin/users`);
      const currentUser = userRes.data.find((u) => u.email === email);
      if (currentUser) {
        setUserId(currentUser.id);
        fetchOrders(currentUser.id);
      }
    };
    fetchUserId();
  }, []);

  const fetchOrders = async (uid) => {
    const res = await axios.get(`http://localhost:8080/api/trading/orders/${uid}`);
    setOrders(res.data);
  };

  const handlePlaceOrder = async () => {
    if (!symbol || !quantity || quantity <= 0) return alert('Fill all fields correctly');
    try {
      await axios.post(`http://localhost:8080/api/trading/order`, {
        userId,
        symbol,
        quantity,
        type: orderType
      });
      alert('Order placed');
      setSymbol('');
      setQuantity('');
      fetchOrders(userId);
    } catch (err) {
      alert('Failed to place order');
      console.error(err);
    }
  };

  const handleCancelOrder = async (orderId) => {
    try {
      await axios.delete(`http://localhost:8080/api/trading/order/${orderId}`);
      fetchOrders(userId);
    } catch (err) {
      alert('Error cancelling order');
    }
  };

  return (
    <div className="container mt-4">
      <h3 className="mb-3">Trading</h3>
      <div className="row g-2 mb-4">
        <div className="col-md-4">
          <input className="form-control" placeholder="Stock Symbol" value={symbol} onChange={(e) => setSymbol(e.target.value)} />
        </div>
        <div className="col-md-4">
          <input type="number" className="form-control" placeholder="Quantity" value={quantity} onChange={(e) => setQuantity(e.target.value)} />
        </div>
        <div className="col-md-4">
          <select className="form-select" value={orderType} onChange={(e) => setOrderType(e.target.value)}>
            <option value="BUY">Buy</option>
            <option value="SELL">Sell</option>
          </select>
        </div>
      </div>
      <button className="btn btn-primary mb-4 w-100" onClick={handlePlaceOrder}>
        Place Order
      </button>

      <h5>Open Orders</h5>
      <table className="table table-bordered mt-2">
        <thead>
          <tr>
            <th>Symbol</th>
            <th>Quantity</th>
            <th>Type</th>
            <th>Status</th>
            <th>Action</th>
          </tr>
        </thead>
        <tbody>
          {orders.map((order) => (
            <tr key={order.id}>
              <td>{order.symbol}</td>
              <td>{order.quantity}</td>
              <td>{order.type}</td>
              <td>{order.status}</td>
              <td>
                <button className="btn btn-sm btn-danger" onClick={() => handleCancelOrder(order.id)}>
                  Cancel
                </button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}

export default Trading;
