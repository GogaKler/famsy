import { PrismaClient } from '@prisma/client';
import * as bcrypt from 'bcrypt';

const prisma = new PrismaClient();

const roundsOfHashing = 10;

async function main() {
  const passwordUser1 = await bcrypt.hash('123', roundsOfHashing);
  const passwordUser2 = await bcrypt.hash('123_test', roundsOfHashing);

  const user1 = await prisma.user.upsert({
    where: { userName: 'GogaKler' },
    update: {
      password: passwordUser1,
    },
    create: {
      firstName: 'Egor',
      lastName: 'Kolesnikov',
      userName: 'GogaKler',
      email: 'gogakler.development@gmail.com',
      password: passwordUser1,
    },
  });

  const user2 = await prisma.user.upsert({
    where: { userName: 'GogaKler_for_test' },
    update: {
      password: passwordUser2,
    },
    create: {
      firstName: 'Egor',
      lastName: 'Kolesnikov',
      userName: 'GogaKler_for_test',
      status: 'Product Owner',
      email: 'gogakler.test@gmail.com',
      password: passwordUser2,
    },
  });

  const accessTokenAuth1 = await bcrypt.hash('accessToken_GogaKler', roundsOfHashing);
  const refreshTokenAuth1 = await bcrypt.hash('refreshToken_GogaKler', roundsOfHashing);
  const accessTokenAuth2 = await bcrypt.hash('accessToken_GogaKler_Test', roundsOfHashing);
  const refreshTokenAuth2 = await bcrypt.hash('refreshToken_GogaKler_Test', roundsOfHashing);

  const auth1 = await prisma.auth.upsert({
    where: { userId: user1.id },
    update: {},
    create: {
      accessToken: accessTokenAuth1,
      refreshToken: refreshTokenAuth1,
      userId: user1.id,
    },
  });

  const auth2 = await prisma.auth.upsert({
    where: { userId: user2.id },
    update: {},
    create: {
      accessToken: accessTokenAuth2,
      refreshToken: refreshTokenAuth2,
      userId: user2.id,
    },
  });

  console.log({ user1, user2, auth1, auth2 });
}

// execute the main function
main()
  .catch((e) => {
    console.error(e);
    process.exit(1);
  })
  .finally(async () => {
    // close Prisma Client at the end
    await prisma.$disconnect();
  });
