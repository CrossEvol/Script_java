import { Module } from '@nestjs/common';
import { BirdsController } from './birds.controller';
import { BirdsService } from './birds.service';
import { birdsProviders } from './birds.providers';
import { DatabaseModule } from '../database/database.module';

@Module({
  imports: [DatabaseModule],
  controllers: [BirdsController],
  providers: [BirdsService, ...birdsProviders],
})
export class BirdsModule {}
