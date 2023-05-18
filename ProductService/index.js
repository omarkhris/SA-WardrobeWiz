import express from 'express';
import cors from 'cors';
import { ConnectDB, CreateSeedData } from './db/products.js';
import ProductRouter from './routers/productrouter.js'

const app = express();
app.disable('x-powered-by');
app.use(cors())
app.use(express.json());

app.use('/products', ProductRouter);

app.get('/', (req, res) => {
    res.send("hello from home")
});
app.use((err, req, res, next) => {
    res.status(500).json({
        success: false,
        message: err.message
    });
})

await ConnectDB();

app.listen(8080, () => console.log("Server listening on port 3000"))