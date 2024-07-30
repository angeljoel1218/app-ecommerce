package business

import (
	"context"

	model "github.com/apexglobal/product-service/pkg/domain"
)

type ProductService interface {
	Create(ctx context.Context, product model.Product) (*model.Product, error)
	FindById(ctx context.Context, id string) (*model.Product, error)
}
