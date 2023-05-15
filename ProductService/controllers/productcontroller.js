import { GetProductByIdDB, GetProductsDB, UpdateProductDB } from '../db/products.js'
export const GetProducts = async (req, res, next) => {
    try {
        const products = await GetProductsDB({});
        res.json({
            success: true,
            data: products
        });
    } catch (error) {
        next(error)
    }
}

export const GetClothes = async (req, res, next) => {
    try {
        const products = await GetProductsDB({ category: 'clothing' });
        res.json({
            success: true,
            data: products
        });
    } catch (error) {
        next(error)
    }
}

export const GetShoes = async (req, res, next) => {
    try {
        const products = await GetProductsDB({ category: 'footwear' });
        res.json({
            success: true,
            data: products
        });
    } catch (error) {
        next(error)
    }
}

export const UpdateProduct = async (req, res, next) => {
    let id = req.params.id;
    let body = req.body;
    let order_qty = body.order_quantity;
    if (order_qty && !isNaN(order_qty)) {
        try {
            const product = await GetProductByIdDB(id);
            if (product) {
                let quantity = product.quantity;
                if (quantity > order_qty) {
                    const result = await UpdateProductDB(id, quantity - order_qty);
                    res.json({
                        success: true,
                        data: result
                    });
                }
                else {
                    res.status(404).json({
                        success: false,
                        data: "invalid order quantity"
                    });
                }

            }
            else {
                res.status(404).json({
                    success: false,
                    data: "product not found"
                });
            }

        } catch (error) {
            next(error)
        }
    }
    else {
        res.status(400).json({
            success: false,
            data: "invalid or missing order quantity in request body"
        })
    }
}