import React from "react";
import Head from "next/head";
import { Button, Center, Container, Grid, Image, Text, Title } from "@mantine/core";
import { VscHeart } from "react-icons/vsc";
import Layout from "src/layout/Layout";

const Oss: React.FC<{ sponsors: any[] }> = ({ sponsors }) => {
  return (
    <Layout>
      <Head>
        <title>JSON Crack | Open Source Supporters</title>
      </Head>
      <Container pt={60}>
        <Image mx="auto" src="assets/oss_banner.webp" radius="md" maw={800} alt="oss banner" />
      </Container>
      <Center pt="lg">
        <Button
          component="a"
          href="https://github.com/sponsors/AykutSarac"
          size="lg"
          color="red"
          leftIcon={<VscHeart />}
          target="_blank"
          fw="bolder"
        >
          BECOME PART OF IT
        </Button>
      </Center>
      <Container py={50}>
        <Title color="dark.4" pb="md">
          Thank you!
        </Title>
        <Text color="dark.5" maw={500}>
          &ldquo;We would like to extend our sincerest gratitude to all of our sponsors for their
          invaluable support and contribution towards JSON Crack.&rdquo;
        </Text>
      </Container>
      <Container>
        <Title color="dark.3" order={3} pb="xl">
          Sponsors
        </Title>
        <Grid gutter={30}>
          {sponsors?.map(sponsor => (
            <Grid.Col span="content" key={sponsor.handle}>
              <a href={sponsor.profile}>
                <Image radius="md" width={"4rem"} src={sponsor.avatar} alt={sponsor.handle} />
                <Text color="dark.3" pt="sm" align="center" fz="xs">
                  {sponsor.handle}
                </Text>
              </a>
            </Grid.Col>
          ))}
        </Grid>
      </Container>
    </Layout>
  );
};

export default Oss;

export async function getStaticProps() {
  const res = await fetch("https://ghs.vercel.app/sponsors/aykutsarac");
  const data = await res.json();

  return {
    props: {
      sponsors: data?.sponsors.reverse() || [],
    },
  };
}
