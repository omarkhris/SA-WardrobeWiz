import express from 'express';

const app = express();

app.use("/", (req, res, next) => {
    res.send("Hello from Product Service")
})
app.listen(3000, () => console.log("Server listening on port 3000"))