import * as mongoose from 'mongoose';

export const BirdSchema = new mongoose.Schema({
  name: String,
  age: Number,
  breed: String,
});
