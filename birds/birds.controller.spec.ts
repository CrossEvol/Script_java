import { Test, TestingModule } from '@nestjs/testing';
import { BirdsController } from './birds.controller';
import { CreateBirdDto } from './dto/create-bird.dto';
import { BirdsService } from './birds.service';

describe('BirdsController', () => {
  let controller: BirdsController;
  let service: BirdsService;

  beforeEach(async () => {
    const module: TestingModule = await Test.createTestingModule({
      controllers: [BirdsController],
      providers: [
        {
          provide: BirdsService,
          useValue: {
            findAll: jest.fn().mockResolvedValue([
              {
                name: 'Bird #1',
                breed: 'Bread #1',
                age: 4,
              },
              {
                name: 'Bird #2',
                breed: 'Breed #2',
                age: 3,
              },
              {
                name: 'Bird #3',
                breed: 'Breed #3',
                age: 2,
              },
            ]),
            create: jest
              .fn()
              .mockImplementation((createBirdDto: CreateBirdDto) =>
                Promise.resolve({ _id: '1', ...createBirdDto }),
              ),
          },
        },
      ],
    }).compile();

    controller = module.get(BirdsController);
    service = module.get(BirdsService);
  });

  it('should be defined', () => {
    expect(controller).toBeDefined();
  });

  describe('create()', () => {
    it('should create a new bird', async () => {
      const createBirdDto: CreateBirdDto = {
        name: 'Bird #1',
        breed: 'Breed #1',
        age: 4,
      };

      expect(controller.create(createBirdDto)).resolves.toEqual({
        _id: '1',
        ...createBirdDto,
      });
    });
  });

  describe('findAll()', () => {
    it('should get an array of birds', () => {
      expect(controller.findAll()).resolves.toEqual([
        {
          name: 'Bird #1',
          breed: 'Bread #1',
          age: 4,
        },
        {
          name: 'Bird #2',
          breed: 'Breed #2',
          age: 3,
        },
        {
          name: 'Bird #3',
          breed: 'Breed #3',
          age: 2,
        },
      ]);
    });
  });
});
