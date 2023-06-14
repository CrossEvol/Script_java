import { Inject, Injectable } from '@nestjs/common';
import { Model } from 'mongoose';
import { CreateBirdDto } from './dto/create-bird.dto';
import { Bird } from './interfaces/bird.interface';

@Injectable()
export class BirdsService {
  constructor(@Inject('BIRD_MODEL') private readonly birdModel: Model<Bird>) {}

  async create(createBirdDto: CreateBirdDto): Promise<Bird> {
    const createdBird = this.birdModel.create(createBirdDto);
    return createdBird;
  }

  async findAll(): Promise<Bird[]> {
    return this.birdModel.find().exec();
  }
}
