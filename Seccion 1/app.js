const express = require("express");
const { pool: db } = require("./database");
const app = express();

app.get("/api/orders", async (req, res) => {
  const userId = Number(req.query.userId);

  if (!Number.isInteger(userId) || userId <= 0) {
    return res.status(400).json({
      error: "El identificador de usuario no es valido",
    });
  }

  try {
    const result = await db.query(
      `SELECT
          o.id AS order_id,
          o.status,
          oi.id AS item_id,
          oi.product_id,
          oi.quantity
       FROM orders o
       LEFT JOIN order_items oi ON oi.order_id = o.id
       WHERE o.user_id = $1
       ORDER BY o.id, oi.id`,
      [userId],
    );

    const ordersMap = new Map();
    for (const row of result.rows) {
      if (!ordersMap.has(row.order_id)) {
        ordersMap.set(row.order_id, {
          id: row.order_id,
          status: row.status,
          items: [],
        });
      }

      if (row.item_id !== null) {
        ordersMap.get(row.order_id).items.push({
          id: row.item_id,
          productId: row.product_id,
          quantity: row.quantity,
        });
      }
    }

    res.status(200).json([...ordersMap.values()]);
  } catch (error) {
    console.error(error);
    res.status(500).json({ error: "No se pudieron consultar los pedidos" });
  }
});

app.listen(3000, () => {
  console.log("Server is running on http://localhost:3000");
});
