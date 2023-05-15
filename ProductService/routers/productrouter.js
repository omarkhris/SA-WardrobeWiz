import { Router } from 'express';
import { GetProducts, GetClothes, GetShoes, UpdateProduct } from '../controllers/productcontroller.js'

let ProductRouter = Router();

ProductRouter.get('/', GetProducts)
ProductRouter.get('/clothes', GetClothes)
ProductRouter.get('/shoes', GetShoes)
ProductRouter.patch('/:id', UpdateProduct)

export default ProductRouter;