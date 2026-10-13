package ds.type.entities.parts;

import arc.math.Interp;
import arc.math.Mathf;
import arc.struct.Seq;
import mindustry.entities.part.RegionPart;

public class KeyFramePart extends RegionPart {
    public Seq<KeyFrameMove> keyMoves = new Seq<>();

    public KeyFramePart(String name) {
        super(name);
    }

    public void moves(KeyFrameMove...moves){
        keyMoves.set(moves);
    }

    public static class KeyFrameMove extends PartMove{
        public float progressStart = 0f;
        public float progressEnd = 1f;

        public float moveX = 0f;
        public float moveY = 0f;
        public float moveRot = 0f;

        public Interp interp = Interp.linear;

        public KeyFrameMove() {}
    }

    @Override
    public void draw(PartParams params) {
        // 1. Получаем текущий глобальный прогресс (от 0.0 до 1.0) через функцию progress
        float currentProg = progress.get(params);

        // Переменные для накопления итогового сдвига
        float totalX = 0f;
        float totalY = 0f;
        float totalRot = 0f;

        // Переменные для отслеживания позиции детали в конце ПРЕДЫДУЩЕГО кадра
        float lastX = 0f;
        float lastY = 0f;
        float lastRot = 0f;

        // 2. Итерируемся по всем кадрам анимации
        for (int i = 0; i < keyMoves.size; i++) {
            KeyFrameMove move = keyMoves.get(i);

            if (currentProg >= move.progressStart) {
                if (currentProg <= move.progressEnd) {
                    // Мы находимся ВНУТРИ временного отрезка этого кадра

                    // Вычисляем локальный прогресс внутри кадра (от 0.0 до 1.0)
                    float range = move.progressEnd - move.progressStart;
                    float localProg = range <= 0.001f ? 1f : (currentProg - move.progressStart) / range;

                    // Применяем интерполяцию
                    float alpha = move.interp.apply(localProg);

                    // Интерполируем от точки окончания прошлого кадра до цели текущего
                    totalX = Mathf.lerp(lastX, move.moveX, alpha);
                    totalY = Mathf.lerp(lastY, move.moveY, alpha);
                    totalRot = Mathf.lerp(lastRot, move.moveRot, alpha);

                    // Так как движения последовательные, последующие кадры еще не начались, выходим
                    break;
                } else {
                    // Этот кадр уже полностью ЗАВЕРШЕН.
                    // Запоминаем его финальные значения как стартовые для следующего кадра
                    lastX = move.moveX;
                    lastY = move.moveY;
                    lastRot = move.moveRot;

                    // Если это был последний кадр, и прогресс ушел дальше него — фиксируем финал
                    totalX = lastX;
                    totalY = lastY;
                    totalRot = lastRot;
                }
            } else {
                // Текущий прогресс еще не дошел до этого кадра.
                // Значит, деталь застыла в финальной позиции предыдущего кадра.
                totalX = lastX;
                totalY = lastY;
                totalRot = lastRot;
                break;
            }
        }

        // 3. Временно подменяем move-параметры RegionPart вычисленными значениями
        // Это нужно, чтобы стандартный super.draw() отработал с нашей кастомной геометрией
        float backupX = this.moveX;
        float backupY = this.moveY;
        float backupRot = this.moveRot;

        this.moveX = totalX;
        this.moveY = totalY;
        this.moveRot = totalRot;

        // Передаем управление базовому RegionPart, который возьмет наши moveX/Y/Rot,
        // посчитает mirror (если есть) и отрисует текстуру sprite
        super.draw(params);

        // Возвращаем исходные дефолтные значения (опционально, для чистоты структуры)
        this.moveX = backupX;
        this.moveY = backupY;
        this.moveRot = backupRot;
    }
}
