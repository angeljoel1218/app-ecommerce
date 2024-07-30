package factory

import (
	"github.com/apexglobal/product-service/pkg/application/config"
	repo "github.com/apexglobal/product-service/pkg/domain/repository"
	mong "github.com/apexglobal/product-service/pkg/infrastructure/persistence/mongodb"
	"go.mongodb.org/mongo-driver/mongo"
)

type repository interface {
	Create() repository
}

type ProductRepository struct {
	db         *mongo.Database
	Repository repo.ProductRepository
}

func (p ProductRepository) Create() repository {
	rep := mong.NewProductRepository(p.db, config.Cfg.DbTimeout)
	p.Repository = rep
	return p
}
