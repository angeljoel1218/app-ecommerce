import { ObjectType, Field } from '@nestjs/graphql';
import { Column, Entity, ObjectIdColumn } from 'typeorm';

@Entity({ name: 'customers' })
@ObjectType()
export class Customer {
  @ObjectIdColumn()
  @Field(() => String)
  _id: string;

  @Column()
  @Field(() => String)
  name: string;

  @Column()
  @Field(() => String)
  lastName: string;

  @Column()
  @Field(() => String)
  phone: string;

  @Column({ nullable: true })
  @Field(() => String, { nullable: true })
  address?: string;
}
