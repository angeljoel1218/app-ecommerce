package persistence

import (
	"context"
	"fmt"

	conf "github.com/apexglobal/product-service/pkg/application/config"
	"go.mongodb.org/mongo-driver/mongo"
	"go.mongodb.org/mongo-driver/mongo/options"
)

func DB(ctx context.Context) (*mongo.Database, error) {
	mongoURI := fmt.Sprintf("mongodb://%s:%d/%s", conf.Cfg.DbHost, conf.Cfg.DbPort, conf.Cfg.DbName)

	client, err := mongo.Connect(ctx, options.Client().ApplyURI(mongoURI))
	if err != nil {
		return nil, fmt.Errorf("couldn't connect to mongo: %v", err)
	}

	err = client.Ping(ctx, nil)
	if err != nil {
		return nil, err
	}

	db := client.Database(conf.Cfg.DbName)

	return db, nil
}
