import dotenv from 'dotenv';
dotenv.config();

const mongoUrl = process.env.MONGO_URL;

export const config = {
    dbUrl: mongoUrl,
    options: {
        useNewUrlParser: true,
        useUnifiedTopology: true,
    },
};