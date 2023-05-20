import { Router } from 'express';
import { GetProducts, GetClothes, GetShoes, UpdateProduct, GetProductById } from '../controllers/productcontroller.js'

let ProductRouter = Router();

ProductRouter.get('/', GetProducts)
ProductRouter.get('/clothes', GetClothes)
ProductRouter.get('/shoes', GetShoes)
ProductRouter.patch('/:id', UpdateProduct)
ProductRouter.get('/:id', GetProductById)

export default ProductRouter;