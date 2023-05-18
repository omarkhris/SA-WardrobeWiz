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

export const CreateSeedData = async () => {
    let colors = ["red", "orange", "yellow", "green", "blue", "indigo", "violet", "purple", "pink", "brown", "black", "white", "gray", "silver", "gold", "magenta", "cyan", "teal", "lime", "olive"];
    const clothingItems = ["shirt", "pants", "dress", "jacket", "sweater", "skirt", "shorts", "blouse", "tie", "socks", "shoes", "hat", "gloves", "scarf", "coat", "jeans", "sweatshirt", "hoodie", "tank top", "suit"];
    const footwearItems = ["sneakers", "boots", "sandals", "flats", "heels", "loafers", "oxfords", "slippers", "flip-flops", "wedges", "moccasins", "espadrilles", "platforms", "athletic shoes", "work boots", "hiking boots", "pumps", "ballet flats", "running shoes", "dress shoes"];
    const adjectives = ["stylish", "comfortable", "fashionable", "trendy", "elegant", "casual", "chic", "dapper", "cozy", "sleek", "versatile", "vibrant", "sophisticated", "classy", "modern", "sporty", "formal", "funky", "edgy", "quirky"];
    const seasons = ["winter", "summer", "autumn", "spring"];


    let items = [];
    for (let i = 1; i <= 25; i++) {
        const randomclothingItem = clothingItems[Math.floor(Math.random() * clothingItems.length)];
        const randomfootwearItem = footwearItems[Math.floor(Math.random() * footwearItems.length)];
        const randomColor = colors[Math.floor(Math.random() * colors.length)];
        const randomAdj = adjectives[Math.floor(Math.random() * adjectives.length)];
        const randomSeason = seasons[Math.floor(Math.random() * seasons.length)];
        const randomNumber = Math.floor(Math.random() * 100) + 1;

        let clothingProduct = {
            name: randomColor + ' ' + randomclothingItem,
            description: `${randomAdj} ${randomclothingItem} perfect for ${randomSeason}`,
            color: randomColor,
            category: "clothing",
            quantity: randomNumber
        }
        let footwearProduct = {
            name: randomColor + ' ' + randomfootwearItem,
            description: `${randomAdj} ${randomfootwearItem} perfect for ${randomSeason}`,
            color: randomColor,
            category: "footwear",
            quantity: randomNumber
        }
        items.push(clothingProduct, footwearProduct);
    }
    return await Product.insertMany(items)
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