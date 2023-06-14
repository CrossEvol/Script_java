import { Test, TestingModule } from '@nestjs/testing';
import { BirdsService } from './birds.service';
import { Bird } from './interfaces/bird.interface';
import { Model } from 'mongoose';

const mockBird = {
  name: 'Bird #1',
  breed: 'Breed #1',
  age: 4,
};

const birdsArray = [
  {
    name: 'Bird #1',
    breed: 'Breed #1',
    age: 4,
  },
  {
    name: 'Bird #2',
    breed: 'Breed #2',
    age: 2,
  },
];

describe('BirdService', () => {
  let service: BirdsService;
  let model: Model<Bird>;

  beforeEach(async () => {
    const module: TestingModule = await Test.createTestingModule({
      providers: [
        BirdsService,
        {
          provide: 'BIRD_MODEL',
          useValue: {
            new: jest.fn().mockResolvedValue(mockBird),
            constructor: jest.fn().mockResolvedValue(mockBird),
            find: jest.fn(),
            create: jest.fn(),
            save: jest.fn(),
            exec: jest.fn(),
          },
        },
      ],
    }).compile();

    service = module.get(BirdsService);
    model = module.get<Model<Bird>>('BIRD_MODEL');
  });

  it('should be defined', () => {
    expect(service).toBeDefined();
  });

  it('should return all birds', async () => {
    jest.spyOn(model, 'find').mockReturnValue({
      exec: jest.fn().mockResolvedValueOnce(birdsArray),
    } as any);
    const birds = await service.findAll();
    expect(birds).toEqual(birdsArray);
  });

  it('should insert a new bird', async () => {
    jest.spyOn(model, 'create').mockImplementationOnce(() =>
      Promise.resolve({
        name: 'Bird #1',
        breed: 'Breed #1',
        age: 4,
      }),
    );
    const newBird = await service.create({
      name: 'Bird #1',
      breed: 'Breed #1',
      age: 4,
    });
    expect(newBird).toEqual(mockBird);
  });
});
