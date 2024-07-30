package bootstrap

import (
	"context"

	"github.com/apexglobal/product-service/pkg/application/config"
	http "github.com/apexglobal/product-service/pkg/infrastructure/http"
	"github.com/apexglobal/product-service/pkg/infrastructure/persistence"
)

func Run() error {
	err := config.LoadConfig()
	if err != nil {
		return err
	}

	ctx := context.Background()

	db, err := persistence.DB(ctx)

	if err != nil {
		return err
	}

	ctx, server := http.NewServer(ctx, db)

	return server.Run(ctx)
}
