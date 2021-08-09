package recipes;

import java.util.function.Function;

class CookBookApp {
    private static final RecipeDao DAO = new RecipeDao();

    public static void main(String[] args) {
        create();
        read();
        update();
        delete();
        DAO.close();
    }

    private static void create() {
        Recipe recipe = new Recipe("Kurczak z frytkami", """
                Ziemniaki pokrój w słupki o grubości ok 1cm, możesz je zamarynować w oleju z ziołami i papryką,
                Pierś z kurczaka oczyść z błonek, pokrój w plastry i delikatnie rozbij. Zamarynuj w ulubionych przyprawach.
                Rozgrzej piekarnik do temperatury 180 stopni, najpierw umieść na blaszce frytki, a po 20 minutach dorzuć
                kurczaka i piecz jeszcze 20 minut (łącznie 40 minut). Podawaj z ulubionym sosem lub keczupem.
                """, "ziemniaki, pierś z kurczaka, zioła prowansalskie, oliwa", 30);
        System.out.println("Zapisuję przepis na kurczaka z frytkami");
        DAO.save(recipe);
        System.out.println("Przepis zapisany, jego id to: " + recipe.getId());
    }

    private static void read() {
        DAO.findByTitle("Jajecznica").ifPresentOrElse(
                recipe -> System.out.println("Szukany przepis:\n" + recipe),
                () -> System.out.println("Brak przepisu o takiej nazwie")
        );
    }

    private static void update() {
        Function<Recipe, Recipe> updateRecipePrepTime = recipe -> {
            recipe.setPrepTime(60);
            return recipe;
        };
        DAO.findByTitle("Kurczak z frytkami")
                .map(updateRecipePrepTime)
                .map(DAO::update)
                .filter(b -> b)
                .ifPresent(updated -> System.out.println("Czas przygotowania został zaktualizowany"));
    }

    private static void delete() {
        System.out.println("Usuwam przepis na rosół");
        DAO.findByTitle("Rosół")
                .map(Recipe::getId)
                .map(DAO::delete)
                .ifPresentOrElse(removed -> System.out.println("Przepis został usunięty"),
                        () -> System.out.println("W bazie nie ma przepisu do usunięcia"));
    }
}
