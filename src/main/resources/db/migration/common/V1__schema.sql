CREATE SEQUENCE public.hibernate_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE public.language (
    id bigint NOT NULL,
    locale character varying(5)
);

CREATE TABLE public.mc_option (
    id bigint NOT NULL,
    config_id bigint NOT NULL
);

CREATE TABLE public.mc_option_i18n (
    mc_option_id bigint NOT NULL,
    language_id bigint NOT NULL,
    text character varying(255)
);

CREATE TABLE public.question (
    id bigint NOT NULL
);

CREATE TABLE public.question_config (
    id bigint NOT NULL,
    type character varying(31) NOT NULL
);

CREATE TABLE public.question_i18n (
    question_id bigint NOT NULL,
    language_id bigint NOT NULL,
    text character varying(255)
);

ALTER TABLE ONLY public.language
    ADD CONSTRAINT language_locale_key UNIQUE (locale);

ALTER TABLE ONLY public.language
    ADD CONSTRAINT language_pkey PRIMARY KEY (id);

ALTER TABLE ONLY public.mc_option_i18n
    ADD CONSTRAINT mc_option_i18n_pkey PRIMARY KEY (mc_option_id, language_id);

ALTER TABLE ONLY public.mc_option
    ADD CONSTRAINT pk_mc_option PRIMARY KEY (id);

ALTER TABLE ONLY public.question
    ADD CONSTRAINT pk_question PRIMARY KEY (id);

ALTER TABLE ONLY public.question_config
    ADD CONSTRAINT pk_question_config PRIMARY KEY (id);

ALTER TABLE ONLY public.question_i18n
    ADD CONSTRAINT question_i18n_pkey PRIMARY KEY (question_id, language_id);

ALTER TABLE ONLY public.mc_option
    ADD CONSTRAINT fk_mc_option__config FOREIGN KEY (config_id) REFERENCES public.question_config(id);

ALTER TABLE ONLY public.question_config
    ADD CONSTRAINT fk_question_config__question FOREIGN KEY (id) REFERENCES public.question(id);

ALTER TABLE ONLY public.mc_option_i18n
    ADD CONSTRAINT mc_option_i18n_language_id_fkey FOREIGN KEY (language_id) REFERENCES public.language(id);

ALTER TABLE ONLY public.mc_option_i18n
    ADD CONSTRAINT mc_option_i18n_mc_option_id_fkey FOREIGN KEY (mc_option_id) REFERENCES public.mc_option(id);
