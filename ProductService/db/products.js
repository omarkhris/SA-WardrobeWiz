import { mongoose } from 'mongoose';
import { config } from '../config.js'

import { Schema, model, Types } from "mongoose";

const productSchema = new Schema({
    name: String,
    description: String,
    color: String,
    category: String,
    quantity: Number
});

const Product = model('Product', productSchema);

export const ConnectDB = async () => {
    await mongoose.connect(config.dbUrl, config.options);
    console.log("connected to mongodb")
}

export const GetProductsDB = async (filterObject) => {
    return await Product.find(filterObject)

}

export const UpdateProductDB = async (id, qty) => {
    const filter = { _id: id };
    const update = { quantity: qty };
    return await Product.findOneAndUpdate(filter, update);
}

export const GetProductByIdDB = async (id) => {
    return await Product.findById(id)
}