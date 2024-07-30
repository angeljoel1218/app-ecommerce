package mongodb

import (
	"context"
	"time"

	model "github.com/apexglobal/product-service/pkg/domain"
	"go.mongodb.org/mongo-driver/bson"
	"go.mongodb.org/mongo-driver/mongo"
)

type ProductRepository struct {
	db        *mongo.Database
	dbTimeout time.Duration
}

func NewProductRepository(db *mongo.Database, dbTimeout time.Duration) *ProductRepository {
	return &ProductRepository{
		db:        db,
		dbTimeout: dbTimeout,
	}
}

func (p ProductRepository) Save(ctx context.Context, product model.Product) (*model.Product, error) {
	ctxTimeout, cancel := context.WithTimeout(ctx, p.dbTimeout)
	defer cancel()

	coll := p.db.Collection(productCollection)

	if _, err := coll.InsertOne(ctxTimeout, DocProduct{
		ID:          product.ID,
		Description: product.Description,
		Price:       product.Price,
		Category:    product.Category,
		CreatedAt:   product.CreatedAt,
		UpdatedAt:   product.UpdatedAt,
		DeletedAt:   product.DeletedAt,
	}); err != nil {
		return nil, err
	}
	return &product, nil
}

func (p ProductRepository) FindById(ctx context.Context, id string) (*model.Product, error) {
	var foundProduct *DocProduct

	ctxTimeout, cancel := context.WithTimeout(ctx, p.dbTimeout)
	defer cancel()

	err := p.db.Collection(productCollection).
		FindOne(ctxTimeout, bson.M{"product_id": id, "deleted_at": nil}).
		Decode(&foundProduct)

	if err != nil {
		return nil, err
	}

	if foundProduct == nil {
		return nil, nil
	}

	product := model.NewProductBuilder()

	return product.
		SetId(foundProduct.ID).
		SetDescription(foundProduct.Description).
		SetPrice(foundProduct.Price).
		SetCategory(foundProduct.Category).
		SetCreatedAt(foundProduct.CreatedAt).
		SetUpdatedAt(foundProduct.UpdatedAt).
		SetDeletedAt(foundProduct.DeletedAt).
		Build(), nil
}
