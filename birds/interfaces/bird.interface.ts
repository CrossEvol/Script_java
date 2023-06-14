import { Document } from 'mongoose';

export interface Bird extends Document {
  readonly name: string;
  readonly age: number;
  readonly breed: string;
}
