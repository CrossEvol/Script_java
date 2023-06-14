import { Mongoose } from 'mongoose';
import { BirdSchema } from './schemas/bird.schema';

export const birdsProviders = [
  {
    provide: 'BIRD_MODEL',
    useFactory: (mongoose: Mongoose) => mongoose.model('Bird', BirdSchema),
    inject: ['DATABASE_CONNECTION'],
  },
];
