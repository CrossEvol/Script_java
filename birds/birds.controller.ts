import { Controller, Get, Post, Body } from '@nestjs/common';
import { CreateBirdDto } from './dto/create-bird.dto';
import { BirdsService } from './birds.service';
import { Bird } from './interfaces/bird.interface';

@Controller('birds')
export class BirdsController {
  constructor(private readonly birdsService: BirdsService) {}

  @Post()
  async create(@Body() createBirdDto: CreateBirdDto) {
    return this.birdsService.create(createBirdDto);
  }

  @Get()
  async findAll(): Promise<Bird[]> {
    return this.birdsService.findAll();
  }
}
