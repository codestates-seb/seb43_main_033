export default function Home() {
    return (
        <FooterEl>
        <StyledFooterContainer>
          <StyledLogo>
            <Link to="/">
              <LogoImg itemColor="#F48024" containerColor="#BCBBBB" />
            </Link>
          </StyledLogo>
          <StyledNav>
            <FooterNavContent title="Stack Overflow" items={["Questions", "Help"]} />
            <FooterNavContent title="Products" items={["Teams", "Advertising", "Collectives", "Talent"]} />
            <FooterNavContent
              title="Company"
              items={[
                "About",
                "Press",
                "Work Here",
                "Legal",
                "Privacy Policy",
                "Terms of Service",
                "Contact Us",
                "Cookie Settings",
                "Cookie Policy",
              ]}
            />
            <FooterNavContent
              title="Stack Exchange Network"
              items={[
                "Technology",
                "Culture & recreation",
                "Life & arts",
                "Science",
                "Professional",
                "Business",
                " ",
                "API",
                "Data",
              ]}
            />
          </StyledNav>
          <StyledCopyrightContainer>
            <FooterSNSButtons />
            <StyledCopyright>
              Site design / logo © 2023 Stack Exchange Inc; user contributions licensed under{" "}
              <Link to="https://stackoverflow.com/help/licensing">CC BY-SA</Link>. <span>rev 2023.4.14.43390</span>
            </StyledCopyright>
          </StyledCopyrightContainer>
        </StyledFooterContainer>
      </FooterEl>
    );
  }
  