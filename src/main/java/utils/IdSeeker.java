package utils;

public class IdSeeker
{
    public static Integer find(int[] ids)
    {
        if (ids.length > 0)
        {
            int max = ids[0];

            for (int i = 1; i < ids.length; i++)
            {
                if (ids[i] > max)
                    max = ids[i];
            }

            boolean[] occupiedIds = new boolean[max + 2];

            for (int id : ids)
            {
                occupiedIds[id] = true;
            }

            for (int i = 1; i < occupiedIds.length; i++)
            {
                if (!occupiedIds[i])
                {
                    return i;
                }
            }
        }

        return 1;
    }
}
